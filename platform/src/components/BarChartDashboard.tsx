import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";

import { Bar, BarChart, CartesianGrid, XAxis } from "recharts";
import { ChartConfig, ChartContainer } from "@/components/ui/chart";

import { ChartTooltip, ChartTooltipContent } from "@/components/ui/chart";

import api from "@/utils/api/client";
import { useEffect, useState } from "react";


const chartData = [
  { month: "Enero", desktop: 186, mobile: 80 },
  { month: "Febrero", desktop: 305, mobile: 200 },
  { month: "Marzp", desktop: 237, mobile: 120 },
  { month: "Abril", desktop: 73, mobile: 190 },
  { month: "Mayo", desktop: 209, mobile: 130 },
  { month: "Junio", desktop: 214, mobile: 140 },
];
const chartConfig = {
  desktop: {
    label: "Producto #1",
    color: "hsl(var(--chart-1))",
  },
  mobile: {
    label: "Producto #2",
    color: "hsl(var(--chart-2))",
  },
} satisfies ChartConfig;

export default function BarChartDashboard() {

  return (
    <div>
      <Card x-chunk="dashboard-01-chunk-5">
        <CardHeader>
          <CardTitle>Productos más vendidos por mes</CardTitle>
        </CardHeader>
        <CardContent className="grid gap-8">
          <ChartContainer config={chartConfig}>
            <BarChart accessibilityLayer data={chartData}>
              <CartesianGrid vertical={false} />
              <XAxis
                dataKey="month"
                tickLine={false}
                tickMargin={10}
                axisLine={false}
                tickFormatter={(value) => value.slice(0, 3)}
              />
              <ChartTooltip
                cursor={false}
                content={<ChartTooltipContent indicator="dashed" />}
              />
              <Bar dataKey="desktop" fill="var(--color-desktop)" radius={4} />
              <Bar dataKey="mobile" fill="var(--color-mobile)" radius={4} />
            </BarChart>
          </ChartContainer>
        </CardContent>
      </Card>
    </div>
  );
}
