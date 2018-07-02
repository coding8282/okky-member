#!/usr/bin/env bash

sudo chmod +x /home/ec2-user/okky-member-1.0.0.jar
sudo ln -sf /home/ec2-user/okky-member-1.0.0.jar /etc/init.d/okky-member
sudo service okky-member start
sleep 10s