import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card'

function scoreColor(score) {
  if (score >= 66) return 'var(--rise)'
  if (score >= 40) return 'var(--accent)'
  return 'var(--fall)'
}

export function SectorScoreCard({ sector }) {
  const color = scoreColor(sector.score)

  return (
    <Card>
      <CardHeader className="pb-1">
        <CardTitle className="text-[var(--foreground)]">{sector.name}</CardTitle>
      </CardHeader>
      <CardContent>
        <div className="flex items-center gap-3">
          <span className="text-xl font-bold tabular-nums" style={{ color }}>
            {sector.score}
          </span>
          <div className="h-2 flex-1 overflow-hidden rounded-full bg-[var(--border)]">
            <div
              className="h-full rounded-full transition-all"
              style={{ width: `${sector.score}%`, backgroundColor: color }}
            />
          </div>
        </div>
      </CardContent>
    </Card>
  )
}
