package dev.study.ticketHistory.repository

import dev.study.ticketHistory.entity.TicketHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TicketHistoryRepository : JpaRepository<TicketHistory, Long> {
}