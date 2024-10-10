import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import api, { OrderInfo } from "@/utils/api/orders";
import { useState, useEffect } from "react";
import orders from "@/utils/api/orders";

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
        <CardContent className="grid gap-8">
          <div className="flex items-center gap-4">
            {ordersInfo.map((orderInfo) => (
              <div>
                  <div className="grid gap-1">
                    <p className="text-sm font-medium leading-none">
                      {orderInfo.first_name} {orderInfo.last_name}
                    </p>
                    <p className="text-sm text-muted-foreground">
                      {orderInfo.email}
                    </p>
                </div>
                <div className="ml-auto font-medium">Total de la orden: ${orderInfo.total_price} </div>
              </div>
            ))}
          </div>
        </CardContent>
      </Card>
    </div>
  );
}
