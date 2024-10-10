import { Link, useLocation } from "react-router-dom";
import {
  CircleUser,
  Menu,
  Search,
  ChartArea,
  ShoppingBasket,
  Truck,
  User,
  Bell,
  Coins
} from "lucide-react";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { IoIosAddCircleOutline } from "react-icons/io";
import { Input } from "@/components/ui/input";
import { Sheet, SheetContent, SheetTrigger } from "@/components/ui/sheet";
import { Button } from "@/components/ui/button";
import { useAuth } from "@/hooks/useAuth";
import { useState } from "react"; // Añadimos import de useState

const LINKS = [
  {
    title: "Dashboard",
    icon: ChartArea,
    to: "/dashboard",
  },
  {
    title: "Productos",
    icon: ShoppingBasket,
    to: "/products",
  },
  {
    title: "Envios",
    icon: Truck,
    to: "/shipments",
  },
  {
    title: "Usuarios",
    icon: User,
    to: "/users",
  },
  {
    title: "Agregar",
    icon: IoIosAddCircleOutline,
    to: "/specialists",
  },
  {
    title: "Notificaciones",
    icon: Bell,
    to: "/notifications",
  },
  {
    title: "Donaciones",
    icon: Coins,
    to: "/donations",
  }
];

export default function Header() {
  const { pathname } = useLocation();
  const route = pathname.split("/")[1];
  
  return (
    <div className="hidden border-r bg-muted/40 md:block">
      <div className="flex h-full max-h-screen flex-col gap-2">
        <div className="flex h-14 items-center border-b px-4 lg:h-[60px] lg:px-6">
          <Link
            to=""
            className="grid grid-cols-[4rem_1fr] font-semibold w-full h-full"
          >
            <div className="w-full h-full relative p-3">
              <div className="w-full h-full">
                <img
                  src="/logo_todasBrillamosHeader.png"
                  className="object-contain w-full h-full"
                  alt="Logo"
                />
              </div>
            </div>
            <div className="flex justify-left items-center">
              Todas Brillamos
            </div>
          </Link>
        </div>
        <div className="flex-1">
          <nav className="grid items-start px-2 text-sm font-medium lg:px-4">
            {LINKS.map((link, index) => (
              <Link
                key={index}
                to={link.to}
                className={`flex items-center gap-3 rounded-lg px-3 py-2 transition-all ${
                  route === link.to.split("/")[1]
                    ? "bg-muted text-primary"
                    : "text-muted-foreground"
                }`}
              >
                <link.icon className="h-4 w-4" />
                {link.title}
              </Link>
            ))}
          </nav>
        </div>
      </div>
    </div>
  );
}

export function HeaderMobile() {
  const { pathname } = useLocation();
  const route = pathname.split("/")[1];
  const { signOut } = useAuth();
  const [isOpen, setIsOpen] = useState(false); // Añadimos estado para controlar el Sheet

  function handleSignOut(){
    signOut();
  }

  return (
    <header className="flex h-14 items-center gap-4 border-b bg-muted/40 px-4 lg:h-[60px] lg:px-6">
      <Sheet open={isOpen} onOpenChange={setIsOpen}>
        <SheetTrigger asChild>
          <Button variant="outline" size="icon" className="shrink-0 md:hidden">
            <Menu className="h-5 w-5" />
            <span className="sr-only">Toggle navigation menu</span>
          </Button>
        </SheetTrigger>
        <SheetContent side="left" className="flex flex-col">
          <nav className="grid gap-2 text-lg font-medium">
            {LINKS.map((link, index) => (
              <Link
                key={index}
                to={link.to}
                onClick={() => setIsOpen(false)} // Cerramos el menú al hacer clic
                className={`mx-[-0.65rem] flex items-center gap-4 rounded-xl px-3 py-2 hover:text-foreground ${
                  route === link.to.split("/")[1]
                    ? "text-primary"
                    : "text-muted-foreground"
                }`}
              >
                <link.icon className="h-5 w-5" />
                {link.title}
              </Link>
            ))}
          </nav>
        </SheetContent>
      </Sheet>
      <div className="w-full flex-1">
      </div>
      <DropdownMenu>
        <DropdownMenuTrigger asChild>
          <Button variant="secondary" size="icon" className="rounded-full">
            <CircleUser className="h-5 w-5" />
            <span className="sr-only">Toggle user menu</span>
          </Button>
        </DropdownMenuTrigger>
        <DropdownMenuContent align="end">
          <DropdownMenuLabel>Administrador</DropdownMenuLabel>
          <DropdownMenuItem onClick={handleSignOut}>Cerrar sesión</DropdownMenuItem>
        </DropdownMenuContent>
      </DropdownMenu>
    </header>
  );
}