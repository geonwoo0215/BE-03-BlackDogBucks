package com.prgrms.bdbks.domain.item.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("DefaultOption 테스트")
class DefaultOptionTest {

    @DisplayName("생성 - Item() - 모든 shoutCount 를 지정하지 않으면 0으로 생성 성공한다.")
    @Test
    void constructor_create_success() {
        // given & when
        DefaultOption defaultOption = DefaultOption.builder().build();

        //then
        assertThat(defaultOption.getClassicSyrupCount()).isZero();
        assertThat(defaultOption.getEspressoShotCount()).isZero();
        assertThat(defaultOption.getVanillaSyrupCount()).isZero();
        assertThat(defaultOption.getHazelnutSyrupCount()).isZero();
    }

    @DisplayName("생성 - Item() - 모든 shotCount가 0보다 작거나 9보다 크면 생성에 실패한다")
    @ParameterizedTest
    @ValueSource(ints = {-1, -2, 10, 11, 100, 999, -3, -555})
    void constructor_create_count_over0_and_less_then0_fail(int count) {
        // given & when & then
        assertThrows(IllegalArgumentException.class, () -> DefaultOption.builder()
            .classicSyrupCount(count)
            .espressoShotCount(count)
            .vanillaSyrupCount(count)
            .hazelnutSyrupCount(count)
            .build());
    }

}