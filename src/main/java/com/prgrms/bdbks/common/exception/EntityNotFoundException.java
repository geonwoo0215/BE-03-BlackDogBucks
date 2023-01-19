package com.prgrms.bdbks.common.exception;

import java.util.Arrays;

public class EntityNotFoundException extends BusinessException {

	public EntityNotFoundException(Class<?> entity, Object id) {
		super(String.format("%s 엔티티를 찾을 수 없습니다. by id : %s", entity.getSimpleName(), id.toString()));
	}

	public EntityNotFoundException(Class<?> entity, Object... properties) {
		super(String.format("%s 엔티티를 찾을 수 없습니다. by : %s", entity.getSimpleName(),
			String.join(", ", Arrays.stream(properties).map(Object::toString).toArray(String[]::new))));
	}

}
