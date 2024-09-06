import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Activity, CreditCard, DollarSign, Users } from "lucide-react";

export default function CardsDashboard() {
  return (
    <div>
        <div className="grid gap-4 md:grid-cols-2 md:gap-8 lg:grid-cols-4"> 
        <div>
            <Card x-chunk="dashboard-01-chunk-1">
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
                <CardTitle className="text-sm font-medium">Usuarios registrados</CardTitle>
                <Users className="h-4 w-4 text-muted-foreground" />
            </CardHeader>
            <CardContent>
                <div className="text-2xl font-bold">+100</div>
                <p className="text-xs text-muted-foreground">
                registrados en el último mes
                </p>
            </CardContent>
            </Card>
        </div>

        <div>
            <Card x-chunk="dashboard-01-chunk-0">
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
                <CardTitle className="text-sm font-medium">Ventas totales</CardTitle>
                <DollarSign className="h-4 w-4 text-muted-foreground" />
            </CardHeader>
            <CardContent>
                <div className="text-2xl font-bold">450</div>
                <p className="text-xs text-muted-foreground">
                Ventas del último trimestre
                </p>
            </CardContent>
            </Card>
        </div>

        <div>
            <Card x-chunk="dashboard-01-chunk-2">
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
                <CardTitle className="text-sm font-medium">Ventas</CardTitle>
                <CreditCard className="h-4 w-4 text-muted-foreground" />
            </CardHeader>
            <CardContent>
                <div className="text-2xl font-bold">+5,000</div>
                <p className="text-xs text-muted-foreground">
                +19% más que el mes pasado
                </p>
            </CardContent>
            </Card>
        </div>

        <div>
            <Card x-chunk="dashboard-01-chunk-3">
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
                <CardTitle className="text-sm font-medium">Usuarios activos</CardTitle>
                <Activity className="h-4 w-4 text-muted-foreground" />
            </CardHeader>
            <CardContent>
                <div className="text-2xl font-bold">+120</div>
                <p className="text-xs text-muted-foreground">
                +50 desde la última hora
                </p>
            </CardContent>
            </Card>
        </div>
        </div>
    </div>
  );
}
