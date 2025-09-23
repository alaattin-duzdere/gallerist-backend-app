package com.example.gallerist.utils;

import com.example.gallerist.exceptions.BaseException;
import com.example.gallerist.exceptions.ErrorMessage;
import com.example.gallerist.exceptions.MessageType;
import com.example.gallerist.model.OwnableEntity;
import com.example.gallerist.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Bu sınıf kullanıcı permissionlarını yönetir.
 * <p>İlgili repository'e ulaşabilmek için kullanılacak klasta ilgili entity.</p>
 *
 * @author Alaattin
 */

@Slf4j
@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    private final UserRepository userRepository;

    private final RepositoryResolver repositoryResolver;

    private JpaRepository<OwnableEntity, Long> jpaRepository;

    public CustomPermissionEvaluator(UserRepository userRepository, RepositoryResolver repositoryResolver) {
        this.userRepository = userRepository;
        this.repositoryResolver = repositoryResolver;
    }

    public void setRepository(Class domainClass) {
        this.jpaRepository = repositoryResolver.getRepository(domainClass);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        log.warn("Permission Evaluator has been worked (3 parameters one). Authentication: {}, targetDomainObject: {}, permission: {}", authentication, targetDomainObject, permission);
        return true;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        log.warn("Permission Evaluator has been worked (4 parameters one). Authentication: {}, targetId: {}, targetType: {}, permission: {}", authentication, targetId, targetType, permission);
        if (jpaRepository == null) {
            throw new BaseException(new ErrorMessage(MessageType.General_Exception, "Repository not set in PermissionEvaluator for type: " + targetType));
        }
        OwnableEntity ownableEntity = jpaRepository.findById((Long) targetId).orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.No_Record_Exist, "No record found with id: " + targetId + " in type: " + targetType)));
        Long ownerId = ownableEntity.getOwnerId();
        Long userId = userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.No_Record_Exist, "No user found with username: " + authentication.getName()))).getId();
        log.warn("OwnerId: {}, UserId: {}", ownerId, userId);
        return ownerId.equals(userId);
    }
}