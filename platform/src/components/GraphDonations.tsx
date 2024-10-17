"use client"

import { TrendingUp } from "lucide-react"
import { Area, AreaChart, CartesianGrid, XAxis } from "recharts"
import api, { Donation } from "@/utils/api/donations";
import { useEffect, useState } from "react";
import {
  Card,
  CardContent,
  CardDescription,
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

type DonationWithDate = Donation & { date: Date };

export default function GraphDonations() {
  const [chartData, setChartData] = useState<{ month: string, total: number }[]>([]);

  useEffect(() => {
    api.donations.getDonations().then((donations: Donation[]) => {
      const donationsWithDate: DonationWithDate[] = donations.map(donation => ({
        ...donation,
        date: new Date(), 
      }));

      const groupedData: { [key: string]: number } = donationsWithDate.reduce((acc, donation) => {
        const month = donation.date.toLocaleString('default', { month: 'long' });
        if (!acc[month]) {
          acc[month] = 0;
        }
        acc[month] += donation.amount;
        return acc;
      }, {} as { [key: string]: number });

      const formattedData = Object.keys(groupedData).map(month => ({
        month,
        total: groupedData[month],
      }));

      setChartData(formattedData);
    });
  }, []);

  const chartConfig = {
    desktop: {
      label: "Donations",
      color: "hsl(var(--chart-1))",
    },
  } satisfies ChartConfig;

  return (
    <Card className="h-full">
      <CardHeader>
        <CardTitle>Récord de Donaciones</CardTitle>
        <CardDescription>Donaciones por mes</CardDescription>
      </CardHeader>
      <CardContent className="h-64">
        <ChartContainer config={chartConfig}>
          <AreaChart
            accessibilityLayer
            data={chartData}
            margin={{
              left: 12,
              right: 12,
            }}
            height={200}
          >
            <CartesianGrid vertical={false} />
            <XAxis
              dataKey="month"
              tickLine={false}
              axisLine={false}
              tickMargin={8}
              tickFormatter={(value) => value.slice(0, 3)}
            />
            <ChartTooltip cursor={false} content={<ChartTooltipContent />} />
            <defs>
              <linearGradient id="fillDesktop" x1="0" y1="0" x2="0" y2="1">
                <stop
                  offset="5%"
                  stopColor="var(--color-desktop)"
                  stopOpacity={0.8}
                />
                <stop
                  offset="95%"
                  stopColor="var(--color-desktop)"
                  stopOpacity={0.1}
                />
              </linearGradient>
            </defs>
            <Area
              dataKey="total"
              type="natural"
              fill="url(#fillDesktop)"
              fillOpacity={0.4}
              stroke="var(--color-desktop)"
              stackId="a"
            />
          </AreaChart>
        </ChartContainer>
      </CardContent>
      <CardFooter>
        <div className="flex w-full items-start gap-2 text-sm">
        </div>
      </CardFooter>
    </Card>
  );
}
