package dev.study.seat.repository

import dev.study.seat.entity.Seat
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface SeatRepository : JpaRepository<Seat, Long> {
    // col 과 num 으로 시트 Seat entity 찾기
    @Query("SELECT s FROM Seat s WHERE s.col = :col AND s.num =:num")
    fun findSeatByColAndNum(col:String,num:Int) : Seat?
}
