import UsersRadialChart from "@/components/UsersRadialChart";
import BarChartDashboard from "@/components/BarChartDashboard";
import RecentSalesChart from "@/components/RecentSalesChart";
import CardsDashboard from "@/components/CardsDashboard";

export default function Dashboard() {
  return (
    <div>
      <h2 className="scroll-m-20 border-b pb-2 text-3xl font-semibold tracking-tight first:mt-0">
        Dashboard
      </h2>
      <br />
      <main className="flex flex-1 flex-col gap-4 p-4 md:gap-8 md:p-8">
        <div>
          <CardsDashboard />
        </div>
        <div className="grid gap-2 md:gap-1 lg:grid-cols-3 xl:grid-cols-3">
          <div>
            <RecentSalesChart />
          </div>

          <div>
            <BarChartDashboard />
          </div>

          <div>
            <UsersRadialChart />
          </div>
        </div>
      </main>
    </div>
  );
}
