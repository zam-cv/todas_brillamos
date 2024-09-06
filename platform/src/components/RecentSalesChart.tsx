import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";

import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";

export default function RecentSalesChart() {
  return (
    <div>
      <Card x-chunk="dashboard-01-chunk-5">
        <CardHeader>
          <CardTitle>Últimas ventas</CardTitle>
        </CardHeader>
        <CardContent className="grid gap-8">
          <div className="flex items-center gap-4">
            <Avatar className="hidden h-9 w-9 sm:flex">
              <AvatarImage src="/avatars/01.png" alt="Avatar" />
              <AvatarFallback>OM</AvatarFallback>
            </Avatar>
            <div className="grid gap-1">
              <p className="text-sm font-medium leading-none">Olivia</p>
              <p className="text-sm text-muted-foreground">
                olivia.martin@email.com
              </p>
            </div>
            <div className="ml-auto font-medium">+$450.00</div>
          </div>

          <div className="flex items-center gap-4">
            <Avatar className="hidden h-9 w-9 sm:flex">
              <AvatarImage src="/avatars/02.png" alt="Avatar" />
              <AvatarFallback>M</AvatarFallback>
            </Avatar>
            <div className="grid gap-1">
              <p className="text-sm font-medium leading-none">Marisol</p>
              <p className="text-sm text-muted-foreground">
                marisol.example@email.com
              </p>
            </div>
            <div className="ml-auto font-medium">+$339.00</div>
          </div>

          <div className="flex items-center gap-4">
            <Avatar className="hidden h-9 w-9 sm:flex">
              <AvatarImage src="/avatars/03.png" alt="Avatar" />
              <AvatarFallback>IN</AvatarFallback>
            </Avatar>
            <div className="grid gap-1">
              <p className="text-sm font-medium leading-none">
                Isabella
              </p>
              <p className="text-sm text-muted-foreground">
                isabella.nguyen@email.com
              </p>
            </div>
            <div className="ml-auto font-medium">+$399.00</div>
          </div>

          <div className="flex items-center gap-4">
            <Avatar className="hidden h-9 w-9 sm:flex">
              <AvatarImage src="/avatars/04.png" alt="Avatar" />
              <AvatarFallback>M</AvatarFallback>
            </Avatar>
            <div className="grid gap-1">
              <p className="text-sm font-medium leading-none">Mariana</p>
              <p className="text-sm text-muted-foreground">mariana.example@email.com</p>
            </div>
            <div className="ml-auto font-medium">+$399.00</div>
          </div>

          <div className="flex items-center gap-4">
            <Avatar className="hidden h-9 w-9 sm:flex">
              <AvatarImage src="/avatars/05.png" alt="Avatar" />
              <AvatarFallback>SD</AvatarFallback>
            </Avatar>
            <div className="grid gap-1">
              <p className="text-sm font-medium leading-none">Sofia</p>
              <p className="text-sm text-muted-foreground">
                sofia.davis@email.com
              </p>
            </div>
            <div className="ml-auto font-medium">+$399.00</div>
          </div>
        </CardContent>
      </Card>
    </div>
  );
}
