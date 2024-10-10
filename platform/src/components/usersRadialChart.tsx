import {
  Label,
  PolarGrid,
  PolarRadiusAxis,
  RadialBar,
  RadialBarChart,
} from "recharts";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { ChartConfig, ChartContainer } from "@/components/ui/chart";
import api from "@/utils/api/client";
import { useState, useEffect } from 'react';

const radialChartConfig = {
  visitorCount: {
    label: "Usuarios",
  },
  safari: {
    label: "Safari",
    color: "hsl(var(--chart-2))",
  },
} satisfies ChartConfig;

export default function UsersRadialChart() {
  const [ids, setIds] = useState<number[]>([]);


  useEffect(() => {
    api.client.getClientsIDs().then((ids) => {
      setIds(ids);
    });
  }, []);

  var users = ids.length;

  const radialChartVisitorData = [
    { browserType: "safari", visitorCount: users, fill: "var(--color-safari)" },
  ];

  
  return (
    <div>
      <Card className="flex flex-col ">
        <CardHeader className="items-center pb-0">
          <CardTitle>Total de usuarios</CardTitle>
          <CardDescription>Usuarios registrados al momento</CardDescription>
        </CardHeader>
        <CardContent className="flex-1 pb-0">
          <ChartContainer
            config={radialChartConfig}
            className="mx-auto aspect-square max-h-[250px]"
          >
            <RadialBarChart
              data={radialChartVisitorData}
              startAngle={0}
              endAngle={radialChartVisitorData[0].visitorCount}
              innerRadius={80}
              outerRadius={110}
            >
              <PolarGrid
                gridType="circle"
                radialLines={false}
                stroke="none"
                className="first:fill-muted last:fill-background"
                polarRadius={[86, 74]}
              />
              <RadialBar dataKey="visitorCount" background cornerRadius={10} />
              <PolarRadiusAxis tick={false} tickLine={false} axisLine={false}>
                <Label
                  content={({ viewBox }) => {
                    if (viewBox && "cx" in viewBox && "cy" in viewBox) {
                      return (
                        <text
                          x={viewBox.cx}
                          y={viewBox.cy}
                          textAnchor="middle"
                          dominantBaseline="middle"
                        >
                          <tspan
                            x={viewBox.cx}
                            y={viewBox.cy}
                            className="fill-foreground text-4xl font-bold"
                          >
                            {radialChartVisitorData[0].visitorCount.toLocaleString()}
                          </tspan>
                          <tspan
                            x={viewBox.cx}
                            y={(viewBox.cy || 0) + 24}
                            className="fill-muted-foreground"
                          >
                            Usuarios
                          </tspan>
                        </text>
                      );
                    }
                  }}
                />
              </PolarRadiusAxis>
            </RadialBarChart>
          </ChartContainer>
        </CardContent>
      </Card>
    </div>
  );
}
