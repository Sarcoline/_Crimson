package com.crimson.core.dao;

import com.crimson.core.model.PasswordResetToken;

public interface PasswordResetTokenDAO extends BaseDAO<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
}
