#!/bin/bash
git add . && git commit -m 'Testes de deploy dokku' && git push -f dokku master && git push -f origin master



