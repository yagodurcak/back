import { Context } from "../../../interfaces/context";

export const Contexts:any = 
{
    "jobs":"Trabajos",
    "jobOrders":"Ordenes de trabajo",
    "planes":"Planos",
    "materialStates":"Estado de materiales",
    "job_documents":"Documento de trabajo",
    "job_order_documents":"Documento de OT",
    "purchase_documents":"Documento de compras",
    "purchase_delivery":"Entregas de ordenes de compra"
};


export const contexts:Array<Context> = [ 
    {
        "id":"jobOrders",
        "name":"Ordenes de trabajo"
    },
    {
        "id":"jobs",
        "name":"Trabajos"
    },
    {
        "id":"planes",
        "name":"Planos"
    },
    {
        "id":"materialStates",
        "name":"Estado de materiales"
    },
    {
        "id":"job_documents",
        "name":"Documento de trabajo"
    },
    {
        "id":"job_order_documents",
        "name":"Documento de OT"
    },
    {
        "id":"purchase_delivery",
        "name": "Entregas de ordenes de compra"
    },
    {
        "id":"purchase_documents",
        "name": "Documento de compras"
    }
];



