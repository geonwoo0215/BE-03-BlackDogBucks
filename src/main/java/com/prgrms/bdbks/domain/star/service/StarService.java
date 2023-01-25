package com.prgrms.bdbks.domain.star.service;

import com.prgrms.bdbks.domain.star.entity.Star;
import com.prgrms.bdbks.domain.user.entity.User;

public interface StarService {

	Long create(User user);

	Star findById(Long userId);

	void delete(Long userId);

	void updateCount(Star star, int count);
}
