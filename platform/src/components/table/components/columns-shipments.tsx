import { useState } from "react";
import { ColumnDef } from "@tanstack/react-table";
import { Checkbox } from "@/components/ui/checkbox";
import { Task } from "../data/schema_shipments";
import { DataTableColumnHeader } from "./data-table-column-header-shipments";

export const columns: ColumnDef<Task>[] = [
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
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("Status")}
            </span>
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
  