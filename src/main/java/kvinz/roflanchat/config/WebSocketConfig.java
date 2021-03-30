package kvinz.roflanchat.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import kvinz.roflanchat.domain.User;
import kvinz.roflanchat.jwt.JwtFilter;
import kvinz.roflanchat.jwt.JwtProvider;
import kvinz.roflanchat.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;
import java.util.Objects;

import static org.springframework.util.StringUtils.hasText;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private static final Logger log = LoggerFactory.getLogger(WebSocketConfig.class);

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    //Идет коннект
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    //подписка и префикс
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/queue","/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }



    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

                log.info("in override " + accessor.getCommand());
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    List<String> authorization = accessor.getNativeHeader("Authorization");
                    String bearer = accessor.getFirstNativeHeader("Authorization");
                    log.info("Header auth token: " + authorization);
                    log.info("Header auth token list: " + authorization);
                    String token = null;

                    if (hasText(bearer) && bearer.startsWith("Bearer ")) {
                        token = bearer.substring(7);
                    }
                    if (token != null && jwtProvider.validateToken(token)) {
                        User customUserDetails = jwtProvider.getLoginFromToken(token);
                        log.info("User founded: " + customUserDetails);
                        UsernamePasswordAuthenticationToken  auth =
                                new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
                        System.out.println(token);
                        SecurityContextHolder.getContext().setAuthentication(auth);
                        accessor.setUser(auth);
                    }
                }else if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                    if (Objects.nonNull(authentication))
                        log.info("Disconnected Auth : " + authentication.getName());
                    else
                        log.info("Disconnected Sess : " + accessor.getSessionId());
                }
                return message;
            }
        });
    }

}
