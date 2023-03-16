package com.ghbt.ghbt_starbucks.api.user_has_mobilecard.repository;

import com.ghbt.ghbt_starbucks.api.user_has_mobilecard.model.UserHasMobileCard;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserHasMobileCardRepository extends JpaRepository<UserHasMobileCard, Long> {

    @Query("select uhc from UserHasMobileCard uhc join fetch uhc.user u join fetch uhc.mobileCard m where u.id=:userId")
    List<UserHasMobileCard> findUserHasMobileCardsByUserId(@Param("userId") Long userId);

    @Query("select uhc from UserHasMobileCard uhc join fetch uhc.user u join fetch uhc.mobileCard m where u.id=:userId and m.id=:mobileCardId")
    Optional<UserHasMobileCard> findUserHasMobileCardByUserIdAndMobileCardId(@Param("userId") Long userId,
        @Param("mobileCardId") Long mobileCardId);
}