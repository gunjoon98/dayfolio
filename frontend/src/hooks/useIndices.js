import { useQuery } from '@tanstack/react-query'
import { fetchIndices, fetchSectors } from '@/api/indices'

// 진짜 실시간(WebSocket)이 아니라 "주기적으로 다시 불러와서 실시간처럼 보이게" 하는 1단계 전략.
// 30초마다 재조회 — 백엔드가 배치로 적재하는 구조라 그보다 더 짧게 잡을 이유는 없음.
const POLL_INTERVAL_MS = 30_000

export function useIndices() {
  return useQuery({
    queryKey: ['main', 'indices'],
    queryFn: fetchIndices,
    refetchInterval: POLL_INTERVAL_MS,
  })
}

export function useSectors() {
  return useQuery({
    queryKey: ['main', 'sectors'],
    queryFn: fetchSectors,
    refetchInterval: POLL_INTERVAL_MS,
  })
}
