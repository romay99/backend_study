package dev.study.repository.ticketHistory

import dev.study.entity.ticketHistory.TicketHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TicketHistoryRepository : JpaRepository<TicketHistory, Long> {
}