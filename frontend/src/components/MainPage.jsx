import { useIndices, useSectors } from '@/hooks/useIndices'
import { IndexCard } from '@/components/IndexCard'
import { SectorScoreCard } from '@/components/SectorScoreCard'
import { Card } from '@/components/ui/card'
import { Skeleton } from '@/components/ui/skeleton'

export function MainPage() {
  const indicesQuery = useIndices()
  const sectorsQuery = useSectors()

  return (
    <main className="mx-auto max-w-5xl px-4 py-8">
      <header className="mb-6">
        <h1 className="text-xl font-bold">Dayfolio</h1>
        <p className="text-sm text-[var(--muted-foreground)]">오늘의 주요 지수와 섹터 흐름</p>
      </header>

      <section className="mb-8">
        <h2 className="mb-3 text-sm font-semibold text-[var(--muted-foreground)]">주요 지수</h2>
        {indicesQuery.isLoading && <IndexCardsSkeleton />}
        {indicesQuery.isError && (
          <ErrorNotice message="지수 정보를 불러오지 못했습니다." onRetry={indicesQuery.refetch} />
        )}
        {indicesQuery.data && (
          <div className="grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-3">
            {indicesQuery.data
              .slice()
              .sort((a, b) => a.stock.order - b.stock.order)
              .map((item) => (
                <IndexCard key={item.stock.seqStock} item={item} />
              ))}
          </div>
        )}
      </section>

      <section>
        <h2 className="mb-3 text-sm font-semibold text-[var(--muted-foreground)]">섹터 점수</h2>
        {sectorsQuery.isLoading && <SectorCardsSkeleton />}
        {sectorsQuery.isError && (
          <ErrorNotice message="섹터 점수를 불러오지 못했습니다." onRetry={sectorsQuery.refetch} />
        )}
        {sectorsQuery.data && (
          <div className="grid grid-cols-1 gap-4 sm:grid-cols-3">
            {sectorsQuery.data.map((sector) => (
              <SectorScoreCard key={sector.key} sector={sector} />
            ))}
          </div>
        )}
      </section>
    </main>
  )
}

function IndexCardsSkeleton() {
  return (
    <div className="grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-3">
      {Array.from({ length: 3 }).map((_, i) => (
        <Card key={i} className="p-4">
          <Skeleton className="mb-3 h-4 w-16" />
          <Skeleton className="mb-1 h-7 w-24" />
          <Skeleton className="h-4 w-20" />
        </Card>
      ))}
    </div>
  )
}

function SectorCardsSkeleton() {
  return (
    <div className="grid grid-cols-1 gap-4 sm:grid-cols-3">
      {Array.from({ length: 3 }).map((_, i) => (
        <Card key={i} className="p-4">
          <Skeleton className="mb-3 h-4 w-20" />
          <Skeleton className="h-2 w-full" />
        </Card>
      ))}
    </div>
  )
}

function ErrorNotice({ message, onRetry }) {
  return (
    <Card className="flex items-center justify-between p-4 text-sm">
      <span className="text-[var(--muted-foreground)]">{message}</span>
      <button
        type="button"
        onClick={() => onRetry()}
        className="rounded-md border px-3 py-1 text-xs font-medium hover:bg-[var(--border)]"
      >
        다시 시도
      </button>
    </Card>
  )
}
