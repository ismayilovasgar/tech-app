//package com.ismayilov.techapp.aop;
//
//import com.ismayilov.techapp.config.security.UserDetailsImpl;
//import com.ismayilov.techapp.repository.inter.AccountRepository;
//import lombok.AccessLevel;
//import lombok.experimental.FieldDefaults;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Field;
//import java.util.Arrays;
//import java.util.Objects;
//
//@Aspect
//@Component
//@FieldDefaults(level = AccessLevel.PRIVATE)
//public class DebitAccountAspect {
//
//    final AccountRepository accountRepository;
//
//    public DebitAccountAspect(AccountRepository accountRepository) {
//        this.accountRepository = accountRepository;
//    }
//
//    @Before("@annotation(checkDebitAccount)")
//    public void verifyDebitAccountOwner(JoinPoint joinPoint, CheckDebitAccount checkDebitAccount) throws Throwable {
//
//        Object[] args = joinPoint.getArgs();
//        Object dtoArg = Arrays.stream(args)
//                .filter(Objects::nonNull)
//                .filter(a -> hasField(a, checkDebitAccount.field()))
//                .findFirst()
//                .orElseThrow(() -> new RuntimeException("Request içində '" + checkDebitAccount.field() + "' tapılmadı."));
//
//        Integer debitAccountNo = (Integer) getFieldValue(dtoArg, checkDebitAccount.field());
//
//        // Current user
//        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Long userId = userDetails.getUser().getId();
//
//        // DB-dən yoxla
//        boolean ownsAccount = accountRepository.existsByAccountNoAndUserId(debitAccountNo, userId);
//        if (!ownsAccount) {
//            throw new RuntimeException("Siz bu debit hesabının sahibisiniz deyil.");
//        }
//    }
//
//    private boolean hasField(Object obj, String fieldName) {
//        try {
//            obj.getClass().getDeclaredField(fieldName);
//            return true;
//        } catch (NoSuchFieldException e) {
//            return false;
//        }
//    }
//
//    private Object getFieldValue(Object obj, String fieldName) throws IllegalAccessException, NoSuchFieldException {
//        Field field = obj.getClass().getDeclaredField(fieldName);
//        field.setAccessible(true);
//        return field.get(obj);
//    }
//}
//
