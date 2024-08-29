import { useState } from "react";
import { ColumnDef } from "@tanstack/react-table";
import { Checkbox } from "@/components/ui/checkbox";
import { Task } from "../data/schema_products";
import { DataTableColumnHeader } from "./data-table-column-header-users";

export const columns: ColumnDef<Task>[] = [
    {
        accessorKey: "idProducto",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} title="ID" />
        ),
        cell: ({ row }) => {
          return (
            <div className="flex space-x-2">
              <span className="max-w-[500px] truncate font-medium">
                {row.getValue("idProducto")}
              </span>
            </div>
          );
        },
      },
    {
      accessorKey: "nombreProd",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Nombre Producto" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("nombreProd")}
            </span>
          </div>
        );
      },
    },
    {
      accessorKey: "cantidad",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Cantidad" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("cantidad")}
            </span>
          </div>
        );
      },
    },
    
  ];
  