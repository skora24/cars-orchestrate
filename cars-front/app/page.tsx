import Listing from "@/components/listing";
import { env } from "@/env";
import React from "react";

export interface Listing {
    lotNumber: number;
    make: string;
    model: string;
    year: number;
    mileage: number;
    fuel: string;
    city: string;
    transmission: string;
    bid: number;
    thumbnailUrl: string;
}

async function fetchListings(): Promise<[Listing]> {
    const response = await fetch(`${env.GATEWAY_URL}/api/vehicles`, {
        method: "GET",
    });

    if (response.status != 200) {
        throw new Error("ISE");
    }

    return await response.json();
}

export default async function Home() {
    const listings = await fetchListings();

    return (
        <main className="container mx-auto py-10 px-4 md:px-6">
            <h1 className="text-3xl font-bold tracking-tight mb-6">
                Car Listings
            </h1>

            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                {listings.map((car, idx) => (
                    <React.Fragment key={idx}>
                        <Listing car={car} />
                    </React.Fragment>
                ))}
            </div>
        </main>
    );
}
