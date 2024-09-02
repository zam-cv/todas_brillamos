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
    {
      accessorKey: "precio",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Precio" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("precio")}
            </span>
          </div>
        );
      },
    },
    {
      accessorKey: "marca",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Marca" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("marca")}
            </span>
          </div>
        );
      },
    },
    {
      accessorKey: "tamano",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Tamaño" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("tamano")}
            </span>
          </div>
        );
      },
    },
    {
      accessorKey: "material",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Material" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("material")}
            </span>
          </div>
        );
      },
    },
    {
      accessorKey: "absorbencia",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Absorbencia" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("absorbencia")}
            </span>
          </div>
        );
      },
    },
    {
      accessorKey: "cuidadoPiel",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Cuidado de la Piel" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("cuidadoPiel")}
            </span>
          </div>
        );
      },
    },
    {
      accessorKey: "color",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Color" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("color")}
            </span>
          </div>
        );
      },
    },
    {
      accessorKey: "descripcion",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Descripción" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("descripcion")}
            </span>
          </div>
        );
      },
    },
  ];
  