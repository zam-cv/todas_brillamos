"use client"

import { Bar, BarChart, CartesianGrid, Rectangle, XAxis } from "recharts"
import { useState, useEffect } from "react"

import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"
import {
  ChartConfig,
  ChartContainer,
  ChartTooltip,
  ChartTooltipContent,
} from "@/components/ui/chart"

import api, { MostSellProducts } from "@/utils/api/orders"

export const description = "A bar chart with an active bar"

export default function BarChartDashboard() {
  const [mostSell, setMostSell] = useState<MostSellProducts[]>([])
  const [isLoading, setIsLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    const fetchData = async () => {
      try {
        setIsLoading(true)
        const data = await api.orders.getMostSell()
        if (data.length === 0) {
          setError("No hay datos disponibles para mostrar")
        } else {
          setMostSell(data)
          setError(null)
        }
      } catch (err) {
        setError("Error al cargar los datos. Por favor, intente más tarde.")
      } finally {
        setIsLoading(false)
      }
    }

    fetchData()
  }, [])

  const colors = [
    "hsl(var(--chart-1))",
    "hsl(var(--chart-2))",
    "hsl(var(--chart-3))",
  ]

  const chartData = mostSell.map((product, index) => ({
    productName: product.name,
    orderCount: product.order_count,
    fill: colors[index % colors.length]
  }))

  const chartConfig: ChartConfig = mostSell.reduce((config, product, index) => {
    return {
      ...config,
      [product.name]: {
        label: product.name,
        color: colors[index % colors.length],
      },
    }
  }, {})

  const renderContent = () => {
    if (isLoading) {
      return <div className="flex justify-center items-center h-48">Cargando...</div>
    }

    if (error) {
      return (
        <></>
      )
    }

    return (
      <ChartContainer config={chartConfig}>
        <BarChart accessibilityLayer data={chartData}>
          <CartesianGrid vertical={false} />
          <XAxis
            dataKey="productName"
            tickLine={false}
            tickMargin={10}
            axisLine={false}
            tickFormatter={(value) => value}
          />
          <ChartTooltip
            cursor={false}
            content={<ChartTooltipContent hideLabel />}
          />
          <Bar
            dataKey="orderCount"
            strokeWidth={2}
            radius={8}
            activeIndex={2}
            activeBar={({ ...props }) => {
              return (
                <Rectangle
                  {...props}
                  fillOpacity={0.8}
                  stroke={props.payload.fill}
                  strokeDasharray={4}
                  strokeDashoffset={4}
                />
              )
            }}
          />
        </BarChart>
      </ChartContainer>
    )
  }

  return (
    <Card>
      <CardHeader>
        <CardTitle>Productos más vendidos</CardTitle>
      </CardHeader>
      <CardContent>
        {renderContent()}
      </CardContent>
      <CardFooter className="flex-col items-start gap-2 text-sm">
      </CardFooter>
    </Card>
  )
}