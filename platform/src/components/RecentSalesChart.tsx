import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import api, { OrderInfo } from "@/utils/api/orders";
import { useState, useEffect } from "react";

export default function RecentSalesChart() {
  const [ordersInfo, setOrdersInfo] = useState<OrderInfo[]>([]);

  useEffect(() => {
    api.orders.getOrdersInfo().then((ordersInfo) => {
      setOrdersInfo(ordersInfo);
    });
  }, []);

  return (
    <div>
      <Card x-chunk="dashboard-01-chunk-5">
        <CardHeader>
          <CardTitle>Últimas Ordenes</CardTitle>
        </CardHeader>
        <CardContent className="max-h-64 overflow-y-auto grid gap-8"> {/* Añadido max-h-64 y overflow-y-auto */}
          <div className="flex flex-col gap-4">
            {ordersInfo.map((orderInfo) => (
              <div key={orderInfo.id} className="border-b pb-4">
                <div className="grid gap-1">
                  <p className="text-sm font-medium leading-none">
                    {orderInfo.first_name} {orderInfo.last_name}
                  </p>
                  <p className="text-sm text-muted-foreground">
                    {orderInfo.email}
                  </p>
                </div>
                <div className="ml-auto font-medium">
                  Total de la orden: ${orderInfo.total_price}
                </div>
              </div>
            ))}
          </div>
        </CardContent>
      </Card>
    </div>
  );
}
