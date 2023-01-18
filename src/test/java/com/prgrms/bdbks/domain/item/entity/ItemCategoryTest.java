package com.prgrms.bdbks.domain.item.entity;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DisplayName("ItemCategory 테스트")
class ItemCategoryTest {

	@DisplayName("생성 - ItemCategory - 한글명과 영문명이 empty 가 아니고 30자 이하고 itemType 이 null 이아니면 생성에 성공한다.")
	@Test
	void constructor_create_success() {
		//given
		ItemType beverage = ItemType.BEVERAGE;
		String name = "리저브 에스프레소";
		String englishName = "Reserve Espresso";

		//when & then
		assertDoesNotThrow(() -> {
			ItemCategory.builder()
				.name(name)
				.englishName(englishName)
				.itemType(beverage)
				.build();
		});
	}

	@DisplayName("생성 - ItemCategory - 한글명이 null이라면 생성에 실패한다.")
	@Test
	void constructor_create_with_name_null_fail() {
		//given
		ItemType beverage = ItemType.BEVERAGE;
		String name = null;
		String englishName = "Reserve Espresso";
		//when
		IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
			ItemCategory.builder()
				.name(name)
				.englishName(englishName)
				.itemType(beverage)
				.build();
		});

		//then
		assertNull(name);
		assertThat(illegalArgumentException.getMessage()).isEqualTo("name 은 빈 값이면 안됩니다.");
	}

	@DisplayName("생성 - ItemCategory - 한글명이 empty 라면 생성에 실패한다.")
	@ParameterizedTest
	@ValueSource(strings = {"", " "})
	void constructor_create_with_name_empty_fail(String name) {
		//given
		ItemType beverage = ItemType.BEVERAGE;
		String englishName = "Reserve Espresso";
		//when
		assertThrows(IllegalArgumentException.class, () -> {
			ItemCategory.builder()
				.name(name)
				.englishName(englishName)
				.itemType(beverage)
				.build();
		});

		//then
		assertThat(name).isBlank();
	}

	@DisplayName("생성 - ItemCategory - 영어명이 null이라면 생성에 실패한다.")
	@Test
	void constructor_create_with_englishName_null_fail() {
		//given
		ItemType beverage = ItemType.BEVERAGE;
		String name = "리저브 에스프레소";
		String englishName = null;
		//when
		IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
			ItemCategory.builder()
				.name(name)
				.englishName(englishName)
				.itemType(beverage)
				.build();
		});

		//then
		assertNull(englishName);
		assertThat(illegalArgumentException.getMessage()).isEqualTo("englishName 은 빈 값이면 안됩니다.");
	}

	@DisplayName("생성 - ItemCategory - 한글명 글자 수가 30자 초과하면 생성에 실패한다.")
	@Test
	void constructor_create_with_name_length_over_30_fail() {
		//given
		ItemType beverage = ItemType.BEVERAGE;
		String name = "세상에서 가장 비싸고 맛있는 커피. 리저브 에스프레소. 엄청 긴 커피.12345678910 이 커피 가격은 999,999,9999,99999원";
		String englishName = "Reserve Espresso";

		//when
		IllegalArgumentException illegalArgumentException = assertThrows(
			IllegalArgumentException.class, () -> ItemCategory.builder()
				.name(name)
				.englishName(englishName)
				.itemType(beverage)
				.build());

		//then
		assertThat(name.length()).isGreaterThan(30);
		assertThat(illegalArgumentException.getMessage()).isEqualTo(
			"name 의 글자수는 1자 이상 50자 이하여야 합니다.");
	}

	@DisplayName("생성 - ItemCategory - 영어명이 글자 수가 30자 초과하면 생성에 실패한다.")
	@Test
	void constructor_create_with_englishName_length_over_30_fail() {
		//given
		ItemType beverage = ItemType.BEVERAGE;
		String name = "리저브 에스프레소";
		String englishName = "This is very expensive and delicious. This is Reserve Espresso.12345678910";

		//when
		IllegalArgumentException illegalArgumentException = assertThrows(
			IllegalArgumentException.class, () -> {
				ItemCategory.builder()
					.name(name)
					.englishName(englishName)
					.itemType(beverage)
					.build();
			});

		//then
		assertThat(englishName.length()).isGreaterThan(30);
		assertEquals("englishName 의 글자수는 1자 이상 50자 이하여야 합니다.",
			illegalArgumentException.getMessage());
	}

}