import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select"

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


export default function Shipments() {
  return (
    <div>
  <h2 className="scroll-m-20 border-b pb-2 text-3xl font-semibold tracking-tight first:mt-0">
          Envíos
      </h2>      
      <br></br>
      <br></br>
      <Table>
      <TableCaption>Envíos actuales</TableCaption>
      <TableHeader>
          <TableRow>
            <TableHead className="w-[300px]">Nombre del producto</TableHead>
            <TableHead>Cantidad</TableHead>
            <TableHead>N. de guía</TableHead>
            <TableHead>Estado del envío</TableHead>
          </TableRow>
      </TableHeader>
      <TableBody>
          <TableRow>
            <TableCell className="font-medium">Producto #1</TableCell>
            <TableCell>45</TableCell>
            <TableCell>ID0001</TableCell>
            <TableCell>
              <Select>
                <SelectTrigger className="w-[180px]">
                  <SelectValue placeholder="Theme" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="recibido">Recibido</SelectItem>
                  <SelectItem value="enviado">Enviado</SelectItem>
                  <SelectItem value="entregado">Entregado</SelectItem>
                </SelectContent>
              </Select>
            </TableCell>
          </TableRow>
      </TableBody>
      </Table>
    </div>
  )
}