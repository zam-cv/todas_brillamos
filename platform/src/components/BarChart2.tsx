"use client";

import { TrendingUp } from "lucide-react";
import {
  Bar,
  BarChart,
  CartesianGrid,
  LabelList,
  XAxis,
  YAxis,
} from "recharts";
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { ChartConfig, ChartContainer, ChartTooltip, ChartTooltipContent } from "@/components/ui/chart";
import { useEffect, useState } from "react";
import api from "@/utils/api/orders"; // Asegúrate de importar tu API correctamente

export const description = "A bar chart with a custom label";

// Configuración para la gráfica
const chartConfig = {
  total_revenue: {
    label: "Ganancias",
    color: "hsl(var(--chart-4))",
  },
  label: {
    color: "hsl(var(--background))",
  },
} satisfies ChartConfig;

export default function BarChart2() {
  const [chartData, setChartData] = useState<{ month: string, total_revenue: number }[]>([]);

  // Llamar a la API para obtener las ganancias por mes
  useEffect(() => {
    api.orders.getMonthlyRevenue().then((data) => {
      setChartData(data);
    });
  }, []);

  return (
    <Card className="h-full"> {/* Asegura que la tarjeta ocupe toda la altura disponible */}
      <CardHeader>
        <CardTitle>Ganancias por mes</CardTitle>
        <CardDescription>Enero - Diciembre 2024</CardDescription>
      </CardHeader>
      <CardContent className="h-64"> {/* Establece una altura fija para el contenido */}
        <ChartContainer config={chartConfig}>
          <BarChart
            accessibilityLayer
            data={chartData} // Los datos dinámicos de la API
            layout="vertical"
            margin={{
              right: 16,
            }}
            height={200}
          >
            <CartesianGrid horizontal={false} />
            <YAxis
              dataKey="month" 
              type="category"
              tickLine={false}
              tickMargin={10}
              axisLine={false}
              tickFormatter={(value) => value.slice(5, 7) + '/' + value.slice(2, 4)} 
              hide={false} 
            />
            <XAxis dataKey="total_revenue" type="number" hide={false} /> 
            <ChartTooltip
              cursor={false}
              content={<ChartTooltipContent indicator="line" />}
            />
            <Bar
              dataKey="total_revenue" // Usar la clave de ganancias
              layout="vertical"
              fill="#e76e50"
              radius={4}
            >
              <LabelList
                dataKey="month"
                position="insideLeft"
                offset={8}
                className="fill-[--color-label]"
                fontSize={12}
              />
              <LabelList
                dataKey="total_revenue"
                position="right"
                offset={8}
                className="fill-foreground"
                fontSize={12}
              />
            </Bar>
          </BarChart>
        </ChartContainer>
      </CardContent>
      <CardFooter className="flex-col items-start gap-2 text-sm">
      </CardFooter>
    </Card>
  );
}
