import { getMockIndices } from './mockIndices'
import { getMockSectors } from './mockSectors'

// 백엔드에 GET /api/main/indices 가 준비되면 이 값을 false로 바꾸거나
// .env에 VITE_API_MOCK=false 를 넣어서 실제 API로 전환한다.
const USE_MOCK = import.meta.env.VITE_API_MOCK !== 'false'

// web 모듈의 Response<T> 포맷: { result_code, data }
async function unwrap(response) {
  const json = await response.json()
  if (json.result_code !== 'SUCCESS') {
    throw new Error(`API 요청 실패: ${json.result_code}`)
  }
  return json.data
}

export async function fetchIndices() {
  if (USE_MOCK) {
    await sleep(150) // 실제 네트워크 느낌을 위한 살짝의 지연
    return getMockIndices()
  }
  const response = await fetch('/api/main/indices')
  return unwrap(response)
}

export async function fetchSectors() {
  if (USE_MOCK) {
    await sleep(150)
    return getMockSectors()
  }
  const response = await fetch('/api/main/sectors')
  return unwrap(response)
}

function sleep(ms) {
  return new Promise((resolve) => setTimeout(resolve, ms))
}
