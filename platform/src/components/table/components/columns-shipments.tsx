import { ColumnDef } from "@tanstack/react-table";
import { Task } from "../data/schema_shipments";
import { DataTableColumnHeader } from "./data-table-column-header-shipments";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select"


export const createColumns = (
  updateOrderStatus: (orderID: number, status: string) => void):  ColumnDef<Task>[] => [
    {
        accessorKey: "Quantity",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} title="Cantidad" />
        ),
        cell: ({ row }) => {
          return (
            <div className="flex space-x-2">
              <span className="max-w-[500px] truncate font-medium">
                {row.getValue("Quantity")}
              </span>
            </div>
          );
        },
      },
    {
      accessorKey: "DeliveryDate",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Fecha de entrega" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("DeliveryDate")}
            </span>
          </div>
        );
      },
    },
    {
      accessorKey: "Status",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Estado" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <Select
            onValueChange={(value) => {
              updateOrderStatus((row.original as any).id, value);
            }}>
              <SelectTrigger className="w-[180px]">
                <SelectValue placeholder={row.getValue("Status")}/>
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="Preparando envio">Preparando envio</SelectItem>
                <SelectItem value="Enviado">Enviado</SelectItem>
                <SelectItem value="Entregado">Entregado</SelectItem>
              </SelectContent>
            </Select>
          </div>
        );
      },
    },
    {
        accessorKey: "ProductID",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} title="ID del producto" />
        ),
        cell: ({ row }) => {
          return (
            <div className="flex space-x-2">
              <span className="max-w-[500px] truncate font-medium">
                {row.getValue("ProductID")}
              </span>
            </div>
          );
        },
      },
      {
        accessorKey: "ClientID",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} title="ID del cliente" />
        ),
        cell: ({ row }) => {
          return (
            <div className="flex space-x-2">
              <span className="max-w-[500px] truncate font-medium">
                {row.getValue("ClientID")}
              </span>
            </div>
          );
        },
      },
    
  ];
  