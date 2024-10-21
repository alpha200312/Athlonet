
const express=require('express');
const app=express();
const connectdb=require('./config/db')
const dotenv=require('dotenv')
dotenv.config({path:"./config/config.env"});
connectdb();
app.use(express.json())
