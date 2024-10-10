"use client"

import { TrendingUp } from "lucide-react"
import { Bar, BarChart, CartesianGrid, Rectangle, XAxis } from "recharts"

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
import { useEffect, useState } from "react"

export const description = "A bar chart with an active bar"

export default function BarChartDashboard() {
  const [mostSell, setMostSell] = useState<MostSellProducts[]>([])

  // Obtener los productos más vendidos de la API
  useEffect(() => {
    api.orders.getMostSell().then((data) => {
      setMostSell(data)
    })
  }, [])

  const colors = [
    "hsl(var(--chart-1))",
    "hsl(var(--chart-2))",
    "hsl(var(--chart-3))",
  ]

  // Mapeo de datos para la gráfica
  const chartData = mostSell.map((product, index) => ({
    productName: product.name, // Nombre del producto
    orderCount: product.order_count, // Cantidad de veces ordenado
    fill: colors[index % colors.length]    // Color de la barra (puedes cambiarlo si prefieres)
  }))

  const chartConfig: ChartConfig = mostSell.reduce((config, product, index) => {
    return {
      ...config,
      [product.name]: {
        label: product.name, // Nombre del producto como label
        color: colors[index % colors.length], // Color del producto
      },
    }
  }, {})

  return (
    <Card>
      <CardHeader>
        <CardTitle>Productos más vendidos</CardTitle>
      </CardHeader>
      <CardContent>
        <ChartContainer config={chartConfig}>
          <BarChart accessibilityLayer data={chartData}>
            <CartesianGrid vertical={false} />
            <XAxis
              dataKey="productName" // Mostrar los nombres de los productos en el eje X
              tickLine={false}
              tickMargin={10}
              axisLine={false}
              tickFormatter={(value) => value} // Muestra directamente el nombre del producto
            />
            <ChartTooltip
              cursor={false}
              content={<ChartTooltipContent hideLabel />}
            />
            <Bar
              dataKey="orderCount" // Cantidad de veces que se ordenó
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
      </CardContent>
      <CardFooter className="flex-col items-start gap-2 text-sm">
        
      </CardFooter>
    </Card>
  )
}
