// 메인화면 하단 섹터 점수 카드용 mock.
// 와이어프레임 분석 문서(.claude/analsis/메인화면분석) 기준 "차트가 아니라 점수로 표현"이라
// 지수 카드와 달리 히스토리 없이 단일 점수만 가짐. 점수 산정 로직은 추후 백엔드에서 결정.

const SECTORS = [
  { key: 'software', name: '소프트웨어', score: 72 },
  { key: 'semiconductor', name: '반도체', score: 58 },
  { key: 'reits', name: '리츠', score: 41 },
]

export function getMockSectors() {
  return SECTORS
}
