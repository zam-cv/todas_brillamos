import { DataTable } from "@/components/table/components/tableDonations/data-table-donations";
import { columns } from "@/components/table/components/tableDonations/columns-donations";
import api, { Donation } from "@/utils/api/donations";
import { useEffect, useState } from "react";

export default function Donations() {
  const [donations, setDonations] = useState<Donation[]>([]);


  // Obtener las donaciones de la base de datos
  useEffect(() => {
    api.donations.getDonations().then((donations) => {
      setDonations(donations);
    });
  }, []);

  return (
    <div>
      <h2 className="scroll-m-20 border-b pb-2 text-3xl font-semibold tracking-tight first:mt-0">
        Donaciones
      </h2>
      <br></br>
      <DataTable data={donations} columns={columns} event_id={1} />
    </div>
  );
}
