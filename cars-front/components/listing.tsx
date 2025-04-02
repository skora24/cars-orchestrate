import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
} from "./ui/card";
import { Button } from "./ui/button";
import Link from "next/link";
import Image from "next/image";
import { Listing as ListingType } from "@/app/page";

export default function Listing({ car }: { car: ListingType }) {
    return (
        <Card className="overflow-hidden">
            <div className="aspect-video relative">
                <Image
                    src={car.thumbnailUrl || "/placeholder.svg"}
                    alt={`${car.make} ${car.model}`}
                    fill
                    className="object-cover"
                />
            </div>
            <CardHeader>
                <div className="flex justify-between items-start">
                    <div>
                        <CardTitle className="text-xl">
                            {car.year} {car.make} {car.model} {car.lotNumber}
                        </CardTitle>
                        <CardDescription className="mt-1">
                            ${car.bid.toLocaleString()}
                        </CardDescription>
                    </div>
                    {/* <Badge variant="outline" className="ml-2">
                        {car.condition}
                    </Badge> */}
                </div>
            </CardHeader>
            <CardContent>
                <div className="grid grid-cols-2 gap-2 text-sm mb-4">
                    <div className="flex items-center gap-1">
                        <span className="text-muted-foreground">Mileage:</span>
                        <span>{car.mileage} mi</span>
                    </div>
                    <div className="flex items-center gap-1">
                        <span className="text-muted-foreground">
                            Transmission:
                        </span>
                        <span>{car.transmission}</span>
                    </div>
                    <div className="flex items-center gap-1">
                        <span className="text-muted-foreground">City:</span>
                        <span>{car.city}</span>
                    </div>
                    <div className="flex items-center gap-1">
                        <span className="text-muted-foreground">Fuel:</span>
                        <span>{car.fuel}</span>
                    </div>
                </div>
                <p className="text-sm text-muted-foreground line-clamp-2">
                    {/* {car.description} */}
                </p>
            </CardContent>
            <CardFooter className="flex justify-between">
                <Button variant="outline" size="sm">
                    Save
                </Button>
                <Button asChild size="sm">
                    <Link href={`/cars/${car.lotNumber}`}>View Details</Link>
                </Button>
            </CardFooter>
        </Card>
    );
}
