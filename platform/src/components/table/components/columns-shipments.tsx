import { useState } from "react";
import { ColumnDef } from "@tanstack/react-table";
import { Checkbox } from "@/components/ui/checkbox";
import { Task } from "../data/schema_shipments";
import { DataTableColumnHeader } from "./data-table-column-header-shipments";

export const columns: ColumnDef<Task>[] = [
    {
        accessorKey: "quantity",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} title="Cantidad" />
        ),
        cell: ({ row }) => {
          return (
            <div className="flex space-x-2">
              <span className="max-w-[500px] truncate font-medium">
                {row.getValue("quantity")}
              </span>
            </div>
          );
        },
      },
    {
      accessorKey: "delivery_date",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Fecha de entrega" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("delivery_date")}
            </span>
          </div>
        );
      },
    },
    {
      accessorKey: "status",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Estado" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("status")}
            </span>
          </div>
        );
      },
    },
    {
        accessorKey: "product_id",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} title="ID del producto" />
        ),
        cell: ({ row }) => {
          return (
            <div className="flex space-x-2">
              <span className="max-w-[500px] truncate font-medium">
                {row.getValue("product_id")}
              </span>
            </div>
          );
        },
      },
      {
        accessorKey: "client_id",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} title="ID del cliente" />
        ),
        cell: ({ row }) => {
          return (
            <div className="flex space-x-2">
              <span className="max-w-[500px] truncate font-medium">
                {row.getValue("client_id")}
              </span>
            </div>
          );
        },
      },
    
  ];
  