import { clsx } from 'clsx'
import { twMerge } from 'tailwind-merge'

// Tailwind 클래스를 조건부로 합칠 때 쓰는 헬퍼 (shadcn/ui 관례)
export function cn(...inputs) {
  return twMerge(clsx(inputs))
}
