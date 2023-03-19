package com.ghbt.ghbt_starbucks.api.user_has_starbucks_card.repository;

import com.ghbt.ghbt_starbucks.api.user_has_starbucks_card.model.UserHasStarbucksCard;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IUserHasStarbucksCardRepository extends JpaRepository<UserHasStarbucksCard, Long> {

    @Query("select uhc from UserHasStarbucksCard uhc join fetch uhc.user u join fetch uhc.starbucksCard m where u.id=:userId")
    List<UserHasStarbucksCard> findByUserId(@Param("userId") Long userId);

    @Query("select uhc from UserHasStarbucksCard uhc join fetch uhc.user u join fetch uhc.starbucksCard m where u.id=:userId and m.id=:starbucksCardId")
    Optional<UserHasStarbucksCard> findByUserIdAndStarbucksCardId(@Param("userId") Long userId,
        @Param("starbucksCardId") Long starbucksCardId);
}