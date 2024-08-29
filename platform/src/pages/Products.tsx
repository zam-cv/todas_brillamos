import { Input } from "@/components/ui/input"
import { Button } from "@/components/ui/button"
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableFooter,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table"

export default function Products() {
  return(
    <div>
      <h2 className="scroll-m-20 border-b pb-2 text-3xl font-semibold tracking-tight first:mt-0">
          Productos
      </h2>
      <div className="flex w-full max-w-sm items-center space-x-2">
        <Input type= "search" placeholder = "search" />
        <Button type= "submit">Añadir Producto</Button>
      </div>

    <br></br>

      <Table>
        <TableCaption>Inventario Actual de productos</TableCaption>
        <TableHeader>
            <TableRow>
              <TableHead className="w-[300px]">Nombre del producto</TableHead>
              <TableHead>Cantidad</TableHead>
              <TableHead>ID de Producto</TableHead>
            </TableRow>
        </TableHeader>
        <TableBody>
            <TableRow>
              <TableCell className="font-medium">Producto #1</TableCell>
              <TableCell>45</TableCell>
              <TableCell>ID0001</TableCell>
            </TableRow>
        </TableBody>
      </Table>

      
    </div>
  ) 
        
  
  
}