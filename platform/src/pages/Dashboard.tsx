import UsersRadialChart from "@/components/UsersRadialChart";
import BarChartDashboard from "@/components/BarChartDashboard";
import RecentSalesChart from "@/components/RecentSalesChart";
import BarChar2 from "@/components/BarChart2";
import PieGraph from "@/components/PieGraph";
import GraphDonations from "@/components/GraphDonations";

export default function Dashboard() {
  return (
    <div>
      
      <h2 className="scroll-m-20 border-b pb-2 text-3xl font-semibold tracking-tight first:mt-0">
        Dashboard
      </h2>
      <br />
      <main className="flex flex-1 flex-col gap-1 md:gap-5 md:p-8">
        <div className="grid md:gap-5 lg:grid-cols-4 xl:grid-cols-3">
          <div>
            <BarChar2 />
          </div>
          <div>
            <PieGraph />
          </div>
          <div>
            <GraphDonations />
                    
          </div>
        </div>
        <div className="grid md:gap-5 lg:grid-cols-3 xl:grid-cols-3">
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
