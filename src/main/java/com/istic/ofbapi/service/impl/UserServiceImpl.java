//package com.istic.ofbapi.service.impl;
//
//import com.istic.ofbapi.exception.AccessDeniedException;
//import com.istic.ofbapi.exception.ResourceNotFoundException;
//import com.istic.ofbapi.exception.UnauthorizedException;
//import com.istic.ofbapi.mapper.UserMapper;
//import com.istic.ofbapi.model.User;
//import com.istic.ofbapi.model.role.RoleName;
//import com.istic.ofbapi.payload.ApiResponse;
//import com.istic.ofbapi.payload.UserIdentityAvailability;
//import com.istic.ofbapi.payload.UserRequest;
//import com.istic.ofbapi.payload.UserResponse;
//import com.istic.ofbapi.repository.UserRepository;
//import com.istic.ofbapi.security.UserPrincipal;
//import com.istic.ofbapi.service.UserService;
//import lombok.AllArgsConstructor;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.stereotype.Service;
//
//import static com.istic.ofbapi.utils.AppConstants.ID;
//import static com.istic.ofbapi.utils.AppConstants.USER;
//
//@Service
//@AllArgsConstructor
//public class UserServiceImpl implements UserService {
//
//    private final UserRepository userRepository;
//
//    private final UserMapper userMapper;
//
//    @Override
//    public UserIdentityAvailability checkUsernameAvailability(String username) {
//        Boolean isAvailable = !userRepository.existsByUsername(username);
//        return new UserIdentityAvailability(isAvailable);
//    }
//
//    @Override
//    public UserIdentityAvailability checkEmailAvailability(String email) {
//        Boolean isAvailable = !userRepository.existsByEmail(email);
//        return new UserIdentityAvailability(isAvailable);
//    }
//
//    @Override
//    public UserResponse readUser(Long id) {
//        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(USER, ID, id));
//        return userMapper.userToUserResponse(user);
//    }
//
//    @Override
//    public UserResponse updateUser(UserRequest newUser, Long id, UserPrincipal currentUser) {
//        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(USER, ID, id));
//        if (user.getId().equals(currentUser.getId())
//                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
//            User updatedUser = userMapper.userRequestToUser(newUser);
//            user.setEmail(updatedUser.getEmail());
//            user.setFirstName(updatedUser.getFirstName());
//            user.setLastName(updatedUser.getLastName());
//            user.setUsername(updatedUser.getUsername());
//            user.setPassword(updatedUser.getPassword());
//            return userMapper.userToUserResponse(userRepository.save(user));
//        }
//        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to edit this sheet");
//        throw new UnauthorizedException(apiResponse);
//
//    }
//
//    @Override
//    public ApiResponse deleteUser(Long id, UserPrincipal currentUser) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException(USER, ID, id));
//        if (!user.getId().equals(currentUser.getId()) || !currentUser.getAuthorities()
//                .contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
//            ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to delete profile of: " + id);
//            throw new AccessDeniedException(apiResponse);
//        }
//
//        userRepository.deleteById(user.getId());
//
//        return new ApiResponse(Boolean.TRUE, "You successfully deleted profile of: " + id);
//    }
//}
