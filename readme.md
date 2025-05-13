# Petal
A simple [Gradle](https://gradle.org) plugin that helps with Spore addon development!

## What does Petal do exactly?
Right now, we're in the early stages of development. Petal currently adds two gradle tasks 'copy', and 'runServer'.
`copy` depends on the `jar` task, and copies the output to `/run/addons/`.
depends on the `copy` task, and launches Spore!

## What's Spore?
Spore is currently a work in progress server jar for Minecraft Indev,
and will require a mod for the client - which we're calling Blossom - that allows players to play Multiplayer on Indev 20100223! 

## How do I use Petal, Spore, and/or Blossom?
Spore/Blossom isn't ready yet, whilst we've made a ton of work on Spore, Blossom is yet to be started.
Once Spore is ready for release, we're making it open source, along with Petal, and Blossom!

# Licence
LGPL-3.0-or-later