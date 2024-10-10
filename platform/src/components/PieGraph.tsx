"use client";

import { useEffect, useState } from "react";
import { Cell, Pie, PieChart } from "recharts";
import api from "@/utils/api/products";

import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import {
  ChartConfig,
  ChartContainer,
  ChartTooltip,
  ChartTooltipContent,
} from "@/components/ui/chart";

export const description = "A donut chart";

// Configuración de la gráfica
const chartConfig = {
  visitors: {
    label: "Visitors",
  },
} satisfies ChartConfig;

// Lista de colores
const colors = [
  "#FF6384", 
  "#36A2EB", 
  "#FFCE56",   
  "#4BC0C0",   
  "#9966FF",   
  "#FF9F40", ];
export default function PieGraph() {
  const [chartData, setChartData] = useState<{ name: string; value: number; fill: string }[]>([]);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    (async () => {
      try {
        const data = await api.product.getProductCategories();
        // Transformar y establecer los datos del gráfico con color
        setChartData(data.map((category, index) => ({
          name: category.CategoryName, // Asegúrate de que este campo se llama así
          value: category.ProductCount,
          fill: colors[index % colors.length], // Asigna un color
        })));
      } catch (err) {
        console.error("Error fetching category data:", err);
        setError("Error al cargar los datos de categorías.");
      }
    })();
  }, []);

  return (
    <Card className="flex flex-col">
      <CardHeader className="items-center pb-0">
        <CardTitle>Productos por categoría</CardTitle>
      </CardHeader>
      <CardContent className="flex-1 pb-0">
        {error ? (
          <div className="text-red-500">{error}</div>
        ) : (
          <ChartContainer
            config={chartConfig}
            className="mx-auto aspect-square max-h-[250px]"
          >
            <PieChart>
              <ChartTooltip
                cursor={false}
                content={<ChartTooltipContent hideLabel />}
              />
              <Pie
                data={chartData}
                dataKey="value"
                nameKey="name"
                innerRadius={60}
                outerRadius={80}
                label
              >
                {chartData.map((entry, index) => (
                  <Cell key={`cell-${index}`} fill={entry.fill} />
                ))}
              </Pie>
            </PieChart>
          </ChartContainer>
        )}
      </CardContent>
      <CardFooter className="flex-col gap-2 text-sm">
        <div className="leading-none text-muted-foreground">
          Mostrando la distribución de productos por categoría
        </div>
      </CardFooter>
    </Card>
  );
}
