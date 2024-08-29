import { useState } from "react";
import { ColumnDef } from "@tanstack/react-table";
import { Checkbox } from "@/components/ui/checkbox";
import { Task } from "../data/schema";
import { DataTableColumnHeader } from "./data-table-column-header";

export const columns: ColumnDef<Task>[] = [
    {
        accessorKey: "idUsuario",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} title="ID" />
        ),
        cell: ({ row }) => {
          return (
            <div className="flex space-x-2">
              <span className="max-w-[500px] truncate font-medium">
                {row.getValue("idUsuario")}
              </span>
            </div>
          );
        },
      },
    {
      accessorKey: "nombre",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Nombre" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("nombre")}
            </span>
          </div>
        );
      },
    },
    {
      accessorKey: "apellido",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Apellido" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("apellido")}
            </span>
          </div>
        );
      },
    },
    {
      accessorKey: "personal_email",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Personal Email" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("personal_email")}
            </span>
          </div>
        );
      },
    },
    {
      accessorKey: "phone",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Phone" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("phone")}
            </span>
          </div>
        );
      },
    },
    {
      accessorKey: "curp",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="CURP" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("curp")}
            </span>
          </div>
        );
      },
    },
    {
      accessorKey: "street",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Dirección" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("street")}
            </span>
          </div>
        );
      },
    },
    {
      accessorKey: "ZIP",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Código Postal" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("ZIP")}
            </span>
          </div>
        );
      },
    },
    
  ];
  