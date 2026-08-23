import Chart from 'react-apexcharts'
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card'
import { cn } from '@/lib/utils'

const numberFormat = new Intl.NumberFormat('ko-KR', { maximumFractionDigits: 2 })

const COUNTRY_LABEL = { US: '미국', KR: '한국' }

export function IndexCard({ item }) {
  const { stock, latest, history } = item
  const isRise = latest.changeValue >= 0
  const changeColor = isRise ? 'text-[var(--rise)]' : 'text-[var(--fall)]'
  const sign = isRise ? '+' : ''

  const series = [
    {
      name: stock.name,
      data: history.map((h) => h.closePrice),
    },
  ]

  const options = {
    chart: { type: 'area', sparkline: { enabled: true }, animations: { enabled: false } },
    stroke: { curve: 'smooth', width: 2 },
    fill: {
      type: 'gradient',
      gradient: { opacityFrom: 0.35, opacityTo: 0 },
    },
    colors: [isRise ? 'oklch(0.65 0.19 25)' : 'oklch(0.62 0.17 250)'],
    tooltip: { enabled: false },
    xaxis: { categories: history.map((h) => h.baseDate) },
  }

  return (
    <Card>
      <CardHeader>
        <div>
          <CardTitle>{COUNTRY_LABEL[stock.country] ?? stock.country}</CardTitle>
          <p className="text-lg font-semibold">{stock.name}</p>
        </div>
        <span className="text-xs text-[var(--muted-foreground)]">{stock.code}</span>
      </CardHeader>
      <CardContent>
        <div className="flex items-end justify-between gap-4">
          <div>
            <p className="text-2xl font-bold tabular-nums">{numberFormat.format(latest.closePrice)}</p>
            <p className={cn('text-sm font-medium tabular-nums', changeColor)}>
              {sign}
              {numberFormat.format(latest.changeValue)} ({sign}
              {latest.changeRate.toFixed(2)}%)
            </p>
          </div>
          <div className="h-14 w-24 shrink-0">
            <Chart options={options} series={series} type="area" height="100%" width="100%" />
          </div>
        </div>
      </CardContent>
    </Card>
  )
}
