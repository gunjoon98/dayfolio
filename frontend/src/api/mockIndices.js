// 백엔드 GET /api/main/indices 가 아직 없어서, 응답 스펙을 미리 정해두고
// 그 모양 그대로 mock 데이터를 만든다. (common/dto/StockResult, StockPriceResult 참고)
//
// 실제 API가 생기면 src/api/indices.js 의 fetchIndices()에서
// 이 mock 대신 실제 fetch 호출로 바꾸면 됨 — 컴포넌트/훅은 그대로 재사용.

const SEED_STOCKS = [
  { seqStock: 1, code: '^IXIC', name: '나스닥', type: 'INDEX', country: 'US', order: 1, basePrice: 17800 },
  { seqStock: 2, code: '^GSPC', name: 'S&P500', type: 'INDEX', country: 'US', order: 2, basePrice: 5600 },
  { seqStock: 3, code: '^KS11', name: '코스피', type: 'INDEX', country: 'KR', order: 3, basePrice: 2650 },
]

// 시드 기반 의사난수 (매 렌더마다 값이 안 튀도록 stock별로 고정된 흐름 생성)
function mulberry32(seed) {
  return function () {
    seed |= 0
    seed = (seed + 0x6d2b79f5) | 0
    let t = Math.imul(seed ^ (seed >>> 15), 1 | seed)
    t = (t + Math.imul(t ^ (t >>> 7), 61 | t)) ^ t
    return ((t ^ (t >>> 14)) >>> 0) / 4294967296
  }
}

function buildHistory(seqStock, basePrice, days = 30) {
  const rand = mulberry32(seqStock * 7919)
  const today = new Date()
  let price = basePrice * 0.97
  const history = []

  for (let i = days - 1; i >= 0; i -= 1) {
    const date = new Date(today)
    date.setDate(date.getDate() - i)
    const drift = (rand() - 0.48) * basePrice * 0.012
    price = Math.max(price + drift, basePrice * 0.85)
    history.push({
      baseDate: date.toISOString().slice(0, 10),
      closePrice: Number(price.toFixed(2)),
    })
  }
  return history
}

function toLatest(history) {
  const last = history[history.length - 1]
  const prev = history[history.length - 2] ?? last
  const changeValue = Number((last.closePrice - prev.closePrice).toFixed(2))
  const changeRate = Number(((changeValue / prev.closePrice) * 100).toFixed(2))
  return {
    baseDate: last.baseDate,
    closePrice: last.closePrice,
    changeValue,
    changeRate,
  }
}

export function getMockIndices() {
  return SEED_STOCKS.map(({ basePrice, ...stock }) => {
    const history = buildHistory(stock.seqStock, basePrice)
    return {
      stock: { ...stock, useYn: true },
      latest: toLatest(history),
      history,
    }
  })
}
