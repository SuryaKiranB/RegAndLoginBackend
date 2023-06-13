//package com.example.RegAndLoginApi.Service;
//
//import com.example.RegAndLoginApi.Entity.*;
//import com.example.RegAndLoginApi.Repository.ConfirmationTokenRepository;
//import com.example.RegAndLoginApi.Repository.FpConfirmationTokenRepository;
//import com.example.RegAndLoginApi.Repository.JwtTokenRepository;
//import com.example.RegAndLoginApi.Repository.UserRepository;
//import com.example.RegAndLoginApi.configuration.JwtService;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.time.LocalDateTime;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class UserServiceImplTest {
//
//    @Mock
//    private JwtTokenRepository jwtTokenRepository;
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private ConfirmationTokenRepository confirmationTokenRepository;
//
//    @Mock
//    private JwtService jwtService;
//
//    @Mock
//    private AuthenticationManager authenticationManager;
//
//    @Mock
//    private FpConfirmationTokenRepository fpConfirmationTokenRepository;
//
//    @InjectMocks
//    private UserServiceImpl underTest;
//
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void registerHere() {
//        User user = User.builder()
//                .firstName("Test")
//                .lastName("User")
//                .email("test@example.com")
//                .password("password")
//                .build();
//        ConfirmationToken confirmationToken = new ConfirmationToken(
//                "token",
//                LocalDateTime.now(),
//                LocalDateTime.now().plusMinutes(15),
//                user
//        );
//        when(confirmationTokenRepository.save(any(ConfirmationToken.class))).thenReturn(confirmationToken);
//        String result = underTest.registerHere(user);
//        verify(userRepository, times(1)).save(any(User.class));
//        ArgumentCaptor<ConfirmationToken> argumentCaptor = ArgumentCaptor.forClass(ConfirmationToken.class);
//        verify(confirmationTokenRepository, times(1)).save(argumentCaptor.capture());
//        ConfirmationToken capturedToken = argumentCaptor.getValue();
//        assertNotNull(capturedToken);
////        assertEquals(user, capturedToken.getUser());
//        assertNotNull(result);
//        assertTrue(result.contains("Registered Successfully and here is the verification token : "));
//    }
//
//    @Test
//    public void testRegisterHereExistingUser() {
//        when(userRepository.existsByEmail(anyString())).thenReturn(true);
//
//        User existingUser2 = User.builder()
//                .firstName("Test")
//                .lastName("User")
//                .email("existing@example.com")
//                .password("password")
//                .role(Role.USER)
//                .enabled(false)
//                .build();
//
//        String result = underTest.registerHere(existingUser2);
//        assertEquals("Email already exists", result);
//    }
//
//
//    @Test
//    void testValidLogin() {
//        User user = User.builder()
//                .firstName("Sag")
//                .lastName("Daas")
//                .email("s1@gmail.com")
//                .password("1234567")
//                .role(Role.USER)
//                .enabled(true)
//                .build();
//        when(userRepository.findByEmail("s1@gmail.com")).thenReturn(user);
//        when(jwtService.generateToken(user)).thenReturn("jwtToken");
//
//        String result = underTest.login("s1@gmail.com", "1234567");
//
//        verify(userRepository, times(1)).findByEmail("s1@gmail.com");
//        verify(jwtService, times(1)).generateToken(user);
//        assertEquals("jwtToken", result);
//    }
//
//    @Test
//    void testInvalidLoginUserNotRegistered() {
//
//        when(userRepository.findByEmail("s1@gmail.com")).thenReturn(null);
//
//        String result = underTest.login("s1@gmail.com", "password");
//
//        verify(userRepository, times(1)).findByEmail("s1@gmail.com");
//        assertEquals("User did not Registered", result);
//    }
//
//    @Test
//    void testUserNotVerified() {
//        User user = User.builder()
//                .firstName("Sag")
//                .lastName("Daas")
//                .email("s1@gmail.com")
//                .password("1234567")
//                .role(Role.USER)
//                .enabled(false)
//                .build();
//        when(userRepository.findByEmail("s1@gmail.com")).thenReturn(user);
//
//        String result = underTest.login("s1@gmail.com", "1234567");
//        verify(userRepository, times(1)).findByEmail("s1@gmail.com");
//        assertEquals("Email not verified", result);
//    }
//
//    @Test
//    void generateToken() {
//        String email = "test@example.com";
//        User user = User.builder()
//                .firstName("Test")
//                .lastName("User")
//                .email(email)
//                .password("password")
//                .role(Role.USER)
//                .enabled(true)
//                .build();
//        userRepository.save(user);
//        when(userRepository.findByEmail(email)).thenReturn(user);
//
//        String token = underTest.generateToken(email);
//
//        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), user);
//
//        ArgumentCaptor<ConfirmationToken> argumentCaptor = ArgumentCaptor.forClass(ConfirmationToken.class);
//        verify(confirmationTokenRepository, times(1)).save(argumentCaptor.capture());
//        ConfirmationToken capturedToken = argumentCaptor.getValue();
//        Assertions.assertThat(capturedToken).isEqualTo(confirmationToken);
////        assertEquals(token, capturedToken.getToken());
////        assertEquals(user, capturedToken.getUser());
////        assertTrue(capturedToken.getExpiresAt().isAfter(LocalDateTime.now()));
//    }
//
//    @Test
//    void verifyToken() {
//        String token = "asdfghjk";
//        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(15);
//        User user = new User();
//        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), expiresAt, user);
//
//        when(confirmationTokenRepository.findByToken(token)).thenReturn(confirmationToken);
//
//        String result = underTest.verifyToken(token);
//        assertEquals("Verification Successful.", result);
//        verify(userRepository, times(1)).save(user);
//        assertTrue(user.getEnabled());
//    }
//
//    @Test
//    void verifyToken_expiredCase() {
//        String token = "asdfghjk";
//        LocalDateTime expiresAt = LocalDateTime.now().minusMinutes(1);
//        User user = User.builder()
//                .firstName("Test")
//                .lastName("User")
//                .email("asdfj@gmail.com")
//                .password("password")
//                .role(Role.USER)
//                .enabled(false)
//                .build();
//        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), expiresAt, user);
//
//        when(confirmationTokenRepository.findByToken(token)).thenReturn(confirmationToken);
//
//        String result = underTest.verifyToken(token);
//        assertEquals("Token got Expired. Click on resend to generate new token", result);
//        assertFalse(user.getEnabled());
//
//    }
//
//    @Test
//    void verifyFpToken() {
//        String token = "asdfghjk";
//        String newPassword = "newPassword";
//        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(15);
//        User user = User.builder()
//                .firstName("Test")
//                .lastName("User")
//                .email("asdfj@gmail.com")
//                .password("password")
//                .role(Role.USER)
//                .enabled(false)
//                .build();
//        FpConfirmationToken fpConfirmationToken = new FpConfirmationToken(token, LocalDateTime.now(), expiresAt, user);
//
//        when(fpConfirmationTokenRepository.findByFpToken(token)).thenReturn(fpConfirmationToken);
//        when(passwordEncoder.encode(newPassword)).thenReturn(newPassword);
//
//        String result = underTest.verifyFpToken(token, newPassword);
//
//        assertEquals("Changed Password Successfully", result);
//        verify(userRepository, times(1)).save(user);
//        assertEquals(user.getPassword(), "newPassword");
//    }
//
//    @Test
//    void verifyFpToken_expiredCase() {
//        String token = "asdfghjk";
//        String newPassword = "newPassword";
//        LocalDateTime expiresAt = LocalDateTime.now().minusMinutes(1);
//        User user = User.builder()
//                .firstName("Test")
//                .lastName("User")
//                .email("asdfj@gmail.com")
//                .password("password")
//                .role(Role.USER)
//                .enabled(false)
//                .build();
//        FpConfirmationToken fpConfirmationToken = new FpConfirmationToken(token, LocalDateTime.now(), expiresAt, user);
//        when(fpConfirmationTokenRepository.findByFpToken(token)).thenReturn(fpConfirmationToken);
//
//        String result = underTest.verifyFpToken(token, newPassword);
//        assertEquals("Token got Expired. Click on resend to generate new token", result);
//    }
//
//    @Test
//    void generateFpToken() {
//        String email = "test@example.com";
//        User user = User.builder()
//                .firstName("Test")
//                .lastName("User")
//                .email(email)
//                .password("password")
//                .role(Role.USER)
//                .enabled(true)
//                .build();
//        userRepository.save(user);
//
//        when(userRepository.findByEmail(email)).thenReturn(user);
//
//        String token = underTest.generateFpToken(email);
//
//        FpConfirmationToken fpConfirmationToken = new FpConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), user);
//        ArgumentCaptor<FpConfirmationToken> argumentCaptor = ArgumentCaptor.forClass(FpConfirmationToken.class);
//        verify(fpConfirmationTokenRepository, times(1)).save(argumentCaptor.capture());
//        FpConfirmationToken capturedToken = argumentCaptor.getValue();
//        Assertions.assertThat(capturedToken).isEqualTo(fpConfirmationToken);
//    }
//
//    @Test
//    void saveUserJwtToken() {
//        User user = User.builder()
//                .firstName("Surya")
//                .lastName("Kiran")
//                .email("s@gmail.com")
//                .password(passwordEncoder.encode("12345"))
//                .role(Role.USER)
//                .enabled(false)
//                .build();
//        String jwtToken = "test-jwt-token";
//        JwtToken expectedJwtToken = new JwtToken(jwtToken, false, false, user);
//
//        underTest.saveUserJwtToken(user, jwtToken);
//
//        ArgumentCaptor<JwtToken> argumentCaptor = ArgumentCaptor.forClass(JwtToken.class);
//        verify(jwtTokenRepository, times(1)).save(argumentCaptor.capture());
//        JwtToken capturedJwtToken = argumentCaptor.getValue();
//        Assertions.assertThat(capturedJwtToken).isEqualTo(expectedJwtToken);
//
//    }
//}