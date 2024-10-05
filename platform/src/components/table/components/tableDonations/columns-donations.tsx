import { useState } from "react";
import { ColumnDef } from "@tanstack/react-table";
import { Checkbox } from "@/components/ui/checkbox";
import { Task } from "@/components/table/components/tableDonations/schema_donations";
import { DataTableColumnHeader } from "./data-table-column-header-donations";

export const columns: ColumnDef<Task>[] = [
    {
        accessorKey: "amount",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} title="Cantidad" />
        ),
        cell: ({ row }) => {
          return (
            <div className="flex space-x-2">
              <span className="max-w-[500px] truncate font-medium">
                {row.getValue("amount")}
              </span>
            </div>
          );
        },
      },
    {
      accessorKey: "description",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Descripción" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("description")}
            </span>
          </div>
        );
      },
    },  
    {
        accessorKey: "date",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} title="Fecha" />
        ),
        cell: ({ row }) => {
          return (
            <div className="flex space-x-2">
              <span className="max-w-[500px] truncate font-medium">
                {row.getValue("date")}
              </span>
            </div>
          );
        },
      },  
  ];
  